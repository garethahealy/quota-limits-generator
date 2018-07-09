/*-
 * #%L
 * GarethHealy :: Quota Limits Generator
 * %%
 * Copyright (C) 2013 - 2018 Gareth Healy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.garethahealy.quotalimitsgenerator.cli.parsers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.garethahealy.quotalimitsgenerator.cli.Application;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamlTemplateProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(YamlTemplateProcessor.class);

    private List<Pair<String, Template>> templates = new ArrayList<>();

    public void init() throws IOException {
        ClassTemplateLoader ctl = new ClassTemplateLoader(Application.class, "/freemarker");
        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] {ctl});

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setTemplateLoader(mtl);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        Pair<String, Template> clusterResourceQuota = new ImmutablePair<String, Template>("ClusterResourceQuota-ForUser", cfg.getTemplate("ClusterResourceQuota-ForUser.ftlh"));
        Pair<String, Template> bestEffortResourceLimits = new ImmutablePair<String, Template>("LimitRange-BestEffortResourceLimits", cfg.getTemplate("LimitRange-BestEffortResourceLimits.ftlh"));
        Pair<String, Template> burstableResourceLimits = new ImmutablePair<String, Template>("LimitRange-BurstableResourceLimits", cfg.getTemplate("LimitRange-BurstableResourceLimits.ftlh"));
        Pair<String, Template> maxImageCounts = new ImmutablePair<String, Template>("LimitRange-MaxImageCounts", cfg.getTemplate("LimitRange-MaxImageCounts.ftlh"));
        Pair<String, Template> bestEffort = new ImmutablePair<String, Template>("ResourceQuota-BestEffort", cfg.getTemplate("ResourceQuota-BestEffort.ftlh"));
        Pair<String, Template> notTerminatingAndNotBestEffort = new ImmutablePair<String, Template>("ResourceQuota-NotTerminating-And-NotBestEffort",
                                                                                  cfg.getTemplate("ResourceQuota-NotTerminating-And-NotBestEffort.ftlh"));
        Pair<String, Template> terminating = new ImmutablePair<String, Template>("ResourceQuota-Terminating", cfg.getTemplate("ResourceQuota-Terminating.ftlh"));

        templates = Arrays.asList(clusterResourceQuota, bestEffortResourceLimits, burstableResourceLimits, maxImageCounts, bestEffort, notTerminatingAndNotBestEffort, terminating);
    }

    public void process(QuotaLimitModel quotaLimitModel) throws IOException, TemplateException {
        Map<String, QuotaLimitModel> root = new HashMap<String, QuotaLimitModel>();
        root.put("model", quotaLimitModel);

        if (!new File(quotaLimitModel.getOutputPath().toString()).mkdirs()) {
            throw new IOException("Failed to create directory for; " + quotaLimitModel.getOutputPath().toString());
        }
        
        for (Pair<String, Template> current : templates) {
            LOG.info("{}/{}.yaml", quotaLimitModel.getOutputPath().toString(), current.getKey());

            File yamlFile = new File(quotaLimitModel.getOutputPath().toString() + "/" + current.getKey() + ".yaml");
            if (!yamlFile.createNewFile()) {
                throw new IOException("Failed to create file for; " + current.getKey());
            }

            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(yamlFile), Charset.forName("UTF-8"));
                current.getValue().process(root, writer);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}

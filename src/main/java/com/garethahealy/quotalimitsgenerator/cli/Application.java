/*-
 * #%L
 * GarethHealy :: Quota Limits Generator
 * %%
 * Copyright (C) 2013 - 2016 Gareth Healy
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
package com.garethahealy.quotalimitsgenerator.cli;

import java.io.IOException;

import com.garethahealy.quotalimitsgenerator.cli.parsers.DefaultCLIParser;
import com.garethahealy.quotalimitsgenerator.cli.parsers.CLIModel;
import com.garethahealy.quotalimitsgenerator.cli.parsers.QuotaLimitModel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Starting...");

        DefaultCLIParser parser = new DefaultCLIParser(new DefaultParser());

        try {
            CommandLine commandLine = parser.parse(args, parser.getOptions());
            CLIModel options = parser.getModel(commandLine);

            LOG.info(options.toString());

            QuotaLimitModel quotaLimitModel = options.calculate();

            LOG.info(quotaLimitModel.toString());

        } catch (IOException | ParseException | NumberFormatException ex) {
            LOG.error("We hit a problem! {}", ExceptionUtils.getStackTrace(ex));

            parser.displayHelp(false);
        }
    }
}

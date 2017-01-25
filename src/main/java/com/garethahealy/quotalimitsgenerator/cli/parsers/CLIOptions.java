/*-
 * #%L
 * GarethHealy :: Quota Limits Generator
 * %%
 * Copyright (C) 2013 - 2017 Gareth Healy
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

import java.net.URI;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class CLIOptions {

    private Map<String, Pair<Integer, Integer>> lines;
    private String instanceType;
    private String qualityOfService;
    private Integer nodeWorkerCount;
    private Boolean isTeamNamespace;
    private Integer requestRatio;
    private URI outputPath;

    public CLIOptions(Map<String, Pair<Integer, Integer>> lines, String instanceType, String qualityOfService,
                      Integer nodeWorkerCount, Boolean isTeamNamespace, Integer requestRatio, URI outputPath) {
        this.lines = lines;
        this.instanceType = instanceType;
        this.qualityOfService = qualityOfService;
        this.outputPath = outputPath;
        this.isTeamNamespace = isTeamNamespace;
        this.requestRatio = requestRatio;
        this.nodeWorkerCount = nodeWorkerCount;
    }

    public QuotaLimitModel calculate() {
        Pair<Integer, Integer> instanceTypeLine = lines.get(instanceType);

        Integer instanceCoreInMillis = instanceTypeLine.getLeft() * 1000;
        Integer instanceMemoryInMb = instanceTypeLine.getRight() * 1000;

        QuotaLimitModel quotaLimitModel = new QuotaLimitModel();

        quotaLimitModel.setInstanceType(instanceType);
        quotaLimitModel.setQualityOfService(qualityOfService);
        quotaLimitModel.setAllocatableNodeCores(instanceCoreInMillis);
        quotaLimitModel.setAllocatableNodeMemory(instanceMemoryInMb);
        quotaLimitModel.setMaxPods(Double.valueOf(Math.floor((instanceMemoryInMb / 500) * nodeWorkerCount)).intValue());

        quotaLimitModel.setTerminatingPodCPU(Double.valueOf(Math.floor(instanceCoreInMillis * 0.5)).intValue());
        quotaLimitModel.setTerminatingPodMemory(Double.valueOf(Math.floor(instanceMemoryInMb * 0.5)).intValue());

        quotaLimitModel.setMaxOrNotTerminatingPodLimitCPU(instanceCoreInMillis * requestRatio);
        quotaLimitModel.setMaxOrNotTerminatingPodLimitMemory(instanceMemoryInMb * requestRatio);
        quotaLimitModel.setMaxOrNotTerminatingPodRequestCPU(instanceCoreInMillis);
        quotaLimitModel.setMaxOrNotTerminatingPodRequestMemory(instanceMemoryInMb);

        quotaLimitModel.setIsTeamNamespace(isTeamNamespace);
        quotaLimitModel.setCpuRequestRatio(requestRatio);
        quotaLimitModel.setMemoryRequestRatio(requestRatio);
        quotaLimitModel.setOutputPath(outputPath);

        return quotaLimitModel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("lines", lines)
            .append("instanceType", instanceType)
            .append("qualityOfService", qualityOfService)
            .append("nodeWorkerCount", nodeWorkerCount)
            .append("calculate", calculate())
            .toString();
    }
}

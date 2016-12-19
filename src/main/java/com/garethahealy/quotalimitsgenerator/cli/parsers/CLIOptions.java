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
package com.garethahealy.quotalimitsgenerator.cli.parsers;

import java.net.URI;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class CLIOptions {

    private Map<String, Pair<Integer, Integer>> lines;
    private String instanceType;
    private Integer nodeCores;
    private Integer nodeMemory;
    private Integer nodeReservedCores;
    private Integer nodeReservedMemory;
    private URI outputPath;

    public CLIOptions(Map<String, Pair<Integer, Integer>> lines, String instanceType, Integer nodeCores, Integer nodeMemory, Integer nodeReservedCores,
                      Integer nodeReservedMemory, URI outputPath) {
        this.lines = lines;
        this.instanceType = instanceType;
        this.nodeCores = nodeCores;
        this.nodeMemory = nodeMemory;
        this.nodeReservedCores = nodeReservedCores;
        this.nodeReservedMemory = nodeReservedMemory;
        this.outputPath = outputPath;
    }

    public QuotaLimitModel calculate() {
        Pair<Integer, Integer> instanceTypeLine = lines.get(instanceType);

        Integer instanceCore = instanceTypeLine.getLeft();
        Integer instanceMemory = instanceTypeLine.getRight();

        Integer allocatableNodeCores = nodeCores - nodeReservedCores;
        Integer allocatableNodeMemory = nodeMemory - nodeReservedMemory;

        Integer instanceCoreOrNodeCore = instanceCore > allocatableNodeCores ? allocatableNodeCores : instanceCore;
        Integer instanceMemoryOrNodeMemory = instanceMemory > allocatableNodeMemory ? allocatableNodeMemory : instanceMemory;

        QuotaLimitModel quotaLimitModel = new QuotaLimitModel();
        quotaLimitModel.setAllocatableNodeCores(allocatableNodeCores);
        quotaLimitModel.setAllocatableNodeMemory(allocatableNodeMemory);
        quotaLimitModel.setMaxPods(((instanceMemory * 1000) / 500) + 3);
        quotaLimitModel.setTerminatingPodCPU(((instanceCoreOrNodeCore * 1000) / 100) * 30);
        quotaLimitModel.setTerminatingPodMemory(((instanceMemoryOrNodeMemory * 1000) / 100) * 30);
        quotaLimitModel.setMaxOrNotTerminatingPodCPU(((instanceCoreOrNodeCore * 1000) / 100) * 80);
        quotaLimitModel.setMaxOrNotTerminatingPodMemory(((instanceMemoryOrNodeMemory * 1000) / 100) * 80);
        quotaLimitModel.setOutputPath(outputPath);

        return quotaLimitModel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("lines", lines)
            .append("instanceType", instanceType)
            .append("nodeCores", nodeCores)
            .append("nodeMemory", nodeMemory)
            .append("nodeReservedCores", nodeReservedCores)
            .append("nodeReservedMemory", nodeReservedMemory)
            .append("lculate", calculate())
            .toString();
    }
}

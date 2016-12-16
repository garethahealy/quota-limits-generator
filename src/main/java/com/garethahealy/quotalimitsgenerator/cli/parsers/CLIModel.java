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

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CLIModel {

    private List<CSVRecord> lines;
    private String instanceType;
    private Integer nodeCores;
    private Integer nodeMemory;
    private Integer nodeReservedCores;
    private Integer nodeReservedMemory;

    public CLIModel() {

    }

    public CLIModel(List<CSVRecord> lines, String instanceType, Integer nodeCores, Integer nodeMemory, Integer nodeReservedCores, Integer nodeReservedMemory) {
        this.lines = lines;
        this.instanceType = instanceType;
        this.nodeCores = nodeCores;
        this.nodeMemory = nodeMemory;
        this.nodeReservedCores = nodeReservedCores;
        this.nodeReservedMemory = nodeReservedMemory;
    }

    public QuotaLimitModel calculate() {
        CSVRecord instanceTypeLine = find();

        Integer instanceCore = Integer.parseInt(instanceTypeLine.get(2));
        Integer instanceMemory = Integer.parseInt(instanceTypeLine.get(3));

        QuotaLimitModel quotaLimitModel = new QuotaLimitModel();
        quotaLimitModel.setAllocatableNodeCores(nodeCores - nodeReservedCores);
        quotaLimitModel.setAllocatableNodeMemory(nodeMemory - nodeReservedMemory);
        quotaLimitModel.setMaxPods(((instanceMemory * 1000) / 500) + 3);

        Integer instanceCoreOrNodeCore = instanceCore > quotaLimitModel.getAllocatableNodeCores() ? quotaLimitModel.getAllocatableNodeCores() : instanceCore;
        Integer instanceMemoryOrNodeMemory = instanceMemory > quotaLimitModel.getAllocatableNodeMemory() ? quotaLimitModel.getAllocatableNodeMemory() : instanceMemory;

        quotaLimitModel.setTerminatingPodCPU(((instanceCoreOrNodeCore * 1000) / 100) * 30);
        quotaLimitModel.setTerminatingPodMemory(((instanceMemoryOrNodeMemory * 1000) / 100) * 30);
        quotaLimitModel.setMaxOrNotTerminatingPodCPU(((instanceCoreOrNodeCore * 1000) / 100) * 80);
        quotaLimitModel.setMaxOrNotTerminatingPodMemory(((instanceMemoryOrNodeMemory * 1000) / 100) * 80);

        return quotaLimitModel;
    }

    private CSVRecord find() {
        CSVRecord answer = null;
        for (CSVRecord current : lines) {
            if (current.get(1).equalsIgnoreCase(instanceType)) {
                answer = current;
                break;
            }
        }

        return answer;
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

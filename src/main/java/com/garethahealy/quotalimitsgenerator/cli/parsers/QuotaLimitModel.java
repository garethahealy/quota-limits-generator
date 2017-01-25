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

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuotaLimitModel {

    private String qualityOfService;
    private String instanceType;
    private Integer allocatableNodeCores;
    private Integer allocatableNodeMemory;
    private Integer maxPods;
    private Integer terminatingPodCPU;
    private Integer terminatingPodMemory;
    private Integer maxOrNotTerminatingPodCPU;
    private Integer maxOrNotTerminatingPodMemory;
    private Integer cpuRequestRatio = 3;
    private Integer memoryRequestRatio = 3;
    private URI outputPath;

    public QuotaLimitModel() {

    }

    public String getQualityOfService() {
        return qualityOfService;
    }

    public void setQualityOfService(String qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public Integer getAllocatableNodeCores() {
        return allocatableNodeCores;
    }

    public void setAllocatableNodeCores(Integer allocatableNodeCores) {
        this.allocatableNodeCores = allocatableNodeCores;
    }

    public Integer getAllocatableNodeMemory() {
        return allocatableNodeMemory;
    }

    public void setAllocatableNodeMemory(Integer allocatableNodeMemory) {
        this.allocatableNodeMemory = allocatableNodeMemory;
    }

    public Integer getMaxPods() {
        return maxPods;
    }

    public void setMaxPods(Integer maxPods) {
        this.maxPods = maxPods;
    }

    public Integer getTerminatingPodCPU() {
        return terminatingPodCPU;
    }

    public void setTerminatingPodCPU(Integer terminatingPodCPU) {
        this.terminatingPodCPU = terminatingPodCPU;
    }

    public Integer getTerminatingPodMemory() {
        return terminatingPodMemory;
    }

    public void setTerminatingPodMemory(Integer terminatingPodMemory) {
        this.terminatingPodMemory = terminatingPodMemory;
    }

    public Integer getMaxOrNotTerminatingPodCPU() {
        return maxOrNotTerminatingPodCPU;
    }

    public void setMaxOrNotTerminatingPodCPU(Integer maxOrNotTerminatingPodCPU) {
        this.maxOrNotTerminatingPodCPU = maxOrNotTerminatingPodCPU;
    }

    public Integer getMaxOrNotTerminatingPodMemory() {
        return maxOrNotTerminatingPodMemory;
    }

    public void setMaxOrNotTerminatingPodMemory(Integer maxOrNotTerminatingPodMemory) {
        this.maxOrNotTerminatingPodMemory = maxOrNotTerminatingPodMemory;
    }

    public Integer getCpuRequestRatio() {
        return cpuRequestRatio;
    }

    public void setCpuRequestRatio(Integer cpuRequestRatio) {
        this.cpuRequestRatio = cpuRequestRatio;
    }

    public Integer getMemoryRequestRatio() {
        return memoryRequestRatio;
    }

    public void setMemoryRequestRatio(Integer memoryRequestRatio) {
        this.memoryRequestRatio = memoryRequestRatio;
    }

    public URI getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(URI outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("qualityOfService", qualityOfService)
            .append("instanceType", instanceType)
            .append("allocatableNodeCores", allocatableNodeCores)
            .append("allocatableNodeMemory", allocatableNodeMemory)
            .append("maxPods", maxPods)
            .append("terminatingPodCPU", terminatingPodCPU)
            .append("terminatingPodMemory", terminatingPodMemory)
            .append("maxOrNotTerminatingPodCPU", maxOrNotTerminatingPodCPU)
            .append("maxOrNotTerminatingPodMemory", maxOrNotTerminatingPodMemory)
            .append("cpuRequestRatio", cpuRequestRatio)
            .append("memoryRequestRatio", memoryRequestRatio)
            .append("outputPath", outputPath)
            .toString();
    }
}

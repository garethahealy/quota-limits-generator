<#--
 #%L
 GarethHealy :: Quota Limits Generator
 %%
 Copyright (C) 2013 - 2016 Gareth Healy
 %%
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 #L%
-->
apiVersion: v1
kind: ResourceQuota
metadata:
  name: notterminating-and-notbesteffort-pod-quota
  annotations:
    instance-type: "small"
    applicable-strategy: "per-project-group"
    creation-strategy: "per-project"
    limits-calculation: "80% of instance-type total, or node total if instance-type > node"
    static-content: "false"
<#if model.qualityOfService == "burstable">
spec:
  hard:
    requests.cpu: "${model.maxOrNotTerminatingPodCPU?c}m"
    requests.memory: "${model.maxOrNotTerminatingPodMemory?c}Mi"
    limits.cpu: "${model.maxOrNotTerminatingPodCPU?c}m"
    limits.memory: "${model.maxOrNotTerminatingPodMemory?c}Mi"
  scopes:
  - NotTerminating
  - NotBestEffort
</#if>

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
kind: LimitRange
metadata:
  name: max-image-counts
  annotations:
    instance-type: "all"
    applicable-strategy: "cluster-wide"
    creation-strategy: "per-project"
    static-content: "true"
spec:
  limits:
  - max:
      storage: "1Gi"
    type: openshift.io/Image
  - max:
      openshift.io/image-tags: 3
      openshift.io/images: 20
    type: openshift.io/ImageStream

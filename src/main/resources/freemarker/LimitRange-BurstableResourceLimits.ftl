apiVersion: v1
kind: LimitRange
metadata:
  name: burstable-resource-limits
  annotations:
    instance-type: "small"
    applicable-strategy: "cluster-wide"
    creation-strategy: "per-project"
    limits-calculation: "80% of instance-type total, or node total if instance-type > node"
    static-content: "false"
spec:
  limits:
  - type: Container
    min:
      cpu: "100m"
      memory: "100Mi"
    max:
      cpu: "3200m"
      memory: "1600Mi"
    default:
      cpu: "3200m"
      memory: "1600Mi"
    defaultRequest:
      cpu: "100m"
      memory: "100Mi"
    maxLimitRequestRatio:
      cpu: 3
      memory: 3

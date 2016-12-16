apiVersion: v1
kind: LimitRange
metadata:
  name: besteffort-resource-limits
  annotations:
    applicable-strategy: "cluster-wide"
    creation-strategy: "per-project"
    static-content: "true"
spec:
  limits:
  - type: Container
    min:
      cpu: "100m"
      memory: "100Mi"
    defaultRequest:
      cpu: "100m"
      memory: "100Mi"

apiVersion: v1
kind: ResourceQuota
metadata:
  name: besteffort-pod-quota
  annotations:
    applicable-strategy: "per-project-group"
    creation-strategy: "per-project"
    static-content: "true"
spec:
  hard:
    pods: 4
  scopes:
  - BestEffort

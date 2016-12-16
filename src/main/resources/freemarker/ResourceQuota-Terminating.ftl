apiVersion: v1
kind: ResourceQuota
metadata:
  name: terminating-pod-quota
  annotations:
    instance-type: "small"
    applicable-strategy: "per-project-group"
    creation-strategy: "per-project"
    limits-calculation: "30% of instance-type total, or node total if instance-type > node"
    static-content: "false"
spec:
  hard:
    pods: 4
    requests.cpu: "600m"
    requests.memory: "1200Mi"
    limits.cpu: "600m"
    limits.memory: "1200Mi"
  scopes:
  - Terminating

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
spec:
  hard:
    requests.cpu: "1600m"
    requests.memory: "3200Mi"
    limits.cpu: "1600m"
    limits.memory: "3200Mi"
  scopes:
  - NotTerminating
  - NotBestEffort

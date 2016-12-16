apiVersion: v1
kind: ClusterResourceQuota
metadata:
  name: for-user
  annotations:
    instance-type: "small"
    applicable-strategy: "per-project-group"
    creation-strategy: "per-project-group"
    pods-calculation: "memory / 500mb + 3"
    static-content: "false"
spec:
  quota:
    hard:
      cpu: "2"
      memory: "4Gi"
      pods: "11"
  selector:
    annotations:
      openshift.io/requester: admin

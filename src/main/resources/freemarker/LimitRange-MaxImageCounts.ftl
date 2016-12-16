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

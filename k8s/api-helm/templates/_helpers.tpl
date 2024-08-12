{{/* Define a helper to generate the full Postgresql connection URL */}}
{{- define "my-spring-app.fullPostgersqlConnectionURL" -}}
jdbc:postgresql://{{ .Release.Name }}-postgresql-service:5432/{{ .Values.postgresql.databaseName }}?createDatabaseIfNotExist=true&characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true
{{- end -}}

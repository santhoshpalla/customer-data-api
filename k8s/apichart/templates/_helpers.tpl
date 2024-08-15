{{/*
Expand the name of the chart.
*/}}
{{- define "apichart.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "apichart.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "apichart.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "apichart.labels" -}}
helm.sh/chart: {{ include "apichart.chart" . }}
{{ include "apichart.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "apichart.selectorLabels" -}}
app.kubernetes.io/name: {{ include "apichart.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "apichart.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "apichart.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/* Define a helper to generate the full Postgresql connection URL */}}
{{- define "my-spring-app.fullPostgersqlConnectionURL" -}}
jdbc:postgresql://{{ .Release.Name }}-postgresql-service:5432/{{ .Values.postgresql.databaseName }}?createDatabaseIfNotExist=true&characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true
{{- end -}}
#!/bin/sh

set -e

ENDPOINT="${S3_ENDPOINT_URL:-http://s3:8333}"
ATTACHMENTS_BUCKET="${ATTACHMENTS_BUCKET_NAME:-challenge-attachments}"
SUBMISSIONS_BUCKET="${SUBMISSIONS_BUCKET_NAME:-challenge-submissions}"

RETRIES=15
SLEEP_INTERVAL=20
COUNT=0

echo "Waiting for SeaweedFS containers to be ready"
while true; do
  # Make a HEAD request and get the HTTP status code
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$ENDPOINT" || echo "000")

  if [ "$STATUS" -eq 200 ] || [ "$STATUS" -eq 403 ]; then
    # 200 = OK, 403 = Access Denied (endpoint is up, might need credentials)
    echo "SeaweedFS S3 endpoint is ready (HTTP $STATUS)"
    break
  fi

  COUNT=$((COUNT + 1))
  if [ "$COUNT" -ge "$RETRIES" ]; then
    echo "Timeout waiting for SeaweedFS S3 endpoint"
    exit 1
  fi

  echo "Endpoint not ready yet (HTTP $STATUS). Retrying in $SLEEP_INTERVAL seconds..."
  sleep $SLEEP_INTERVAL
done

echo "Creating bucket: $ATTACHMENTS_BUCKET"
aws --endpoint-url "$ENDPOINT" s3api create-bucket --bucket "$ATTACHMENTS_BUCKET" || true

echo "Creating bucket: $SUBMISSIONS_BUCKET"
aws --endpoint-url "$ENDPOINT" s3api create-bucket --bucket "$SUBMISSIONS_BUCKET" || true

echo "Bucket initialization complete."
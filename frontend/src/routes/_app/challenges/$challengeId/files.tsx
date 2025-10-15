import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_app/challenges/$challengeId/files')({
  component: RouteComponent,
})

function RouteComponent() {
  return <div>Hello "/challenges/$challengeId/files"!</div>
}

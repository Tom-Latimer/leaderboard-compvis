import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_app/challenges/$challengeId/submit')({
  component: RouteComponent,
})

function RouteComponent() {
  return <div>Hello "/challenges/$challengeId/submit"!</div>
}

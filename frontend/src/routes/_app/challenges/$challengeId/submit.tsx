import {createFileRoute} from '@tanstack/react-router'
import ChallengeSubmitForm from "../../../../components/ChallengeSubmitForm/ChallengeSubmitForm.tsx";

export const Route = createFileRoute('/_app/challenges/$challengeId/submit')({
    component: RouteComponent,
})

function RouteComponent() {
    const {challengeId} = Route.useParams();

    return (
        <>
            <ChallengeSubmitForm challengeId={challengeId}/>
        </>
    );
}

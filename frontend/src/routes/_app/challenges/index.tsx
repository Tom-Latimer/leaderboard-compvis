import {createFileRoute} from '@tanstack/react-router'
import ChallengeList from "../../../components/ChallengeList/ChallengeList.tsx";
import {getChallenges} from "../../../api/challenges/challengeApi.ts";

export const Route = createFileRoute('/_app/challenges/')({
    component: RouteComponent,
    loader: async () => {
        return await getChallenges(0, 10);
    }
})

function RouteComponent() {
    return (

        <ChallengeList challenges={Route.useLoaderData()}/>

    );
}

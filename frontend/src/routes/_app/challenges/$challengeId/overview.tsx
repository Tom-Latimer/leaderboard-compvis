import {createFileRoute} from '@tanstack/react-router'
import {getChallengeOverview} from "../../../../api/challenges/challengeApi.ts";

export const Route = createFileRoute('/_app/challenges/$challengeId/overview')({
    component: RouteComponent,
    loader: async ({params}) => {
        return await getChallengeOverview(params.challengeId);
    }
})

function RouteComponent() {
    const challengeOverview = Route.useLoaderData();

    return (
        <div className="tabbed-page-body">
            <div className="subheader">
                <h2>Challenge Overview</h2>
            </div>
            <div className="content">
                {/* TODO: replace p tag with direct access of description text if it is encoded with HTML*/}
                <p>
                    {challengeOverview.description}
                </p>
            </div>
        </div>
    );
}

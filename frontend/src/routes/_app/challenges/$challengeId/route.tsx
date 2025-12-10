import '../challenges.css';
import {createFileRoute, Link, Outlet} from '@tanstack/react-router'
import {getChallengeById} from "../../../../api/challenges/challengeApi.ts";

export const Route = createFileRoute('/_app/challenges/$challengeId')({
    component: RouteComponent,
    notFoundComponent: NotFoundComponent,
    loader: async ({params}) => {
        const challengeId = params.challengeId;
        return await getChallengeById(challengeId);
    }
})

function RouteComponent() {
    const challenge = Route.useLoaderData();

    if (!challenge) {
        return (<div>Could not load challenge details</div>);
    }

    return (
        <>
            <div className="tabbed-page-header">
                <div className="constrainer">
                    <h1>{challenge.title}</h1>
                    <div className="tabbed-container">
                        <div className="tabbed-nav">
                            <Link to="/challenges/$challengeId/overview" params={{challengeId: challenge.id}}
                                  className="tabbed-nav-link"
                                  activeProps={{className: "tabbed-nav-link-active"}}>Overview</Link>
                            <Link to="/challenges/$challengeId/files" params={{challengeId: challenge.id}}
                                  className="tabbed-nav-link"
                                  activeProps={{className: "tabbed-nav-link-active"}}>Datasets</Link>
                            <Link to="/challenges/$challengeId/leaderboard" params={{challengeId: challenge.id}}
                                  className="tabbed-nav-link"
                                  activeProps={{className: "tabbed-nav-link-active"}}>Leaderboard</Link>
                        </div>
                        <div className="tabbed-nav">
                            <Link to="/challenges/$challengeId/submit" params={{challengeId: challenge.id}}
                                  className="tabbed-nav-link"
                                  activeProps={{className: "tabbed-nav-link-active"}}>Submit</Link>
                        </div>
                    </div>
                </div>
            </div>
            <div className="constrainer">
                <Outlet/>
            </div>
        </>
    );
}

function NotFoundComponent() {

    const {challengeId} = Route.useParams()
    return (
        <div>
            <p>
                Sorry! The challenge with id {challengeId} could not be found
            </p>
        </div>
    );
}

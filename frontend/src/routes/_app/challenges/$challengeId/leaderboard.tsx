import {createFileRoute} from '@tanstack/react-router'
import Leaderboard from "./-components/Leaderboard/Leaderboard.tsx";
import {PaginatedWrapperWithSorting} from "../../../../components/pagination/PaginatedWrapperWithSorting.tsx";
import {getSubmissionsByChallenge} from "../../../../api/challenges/submissionApi.ts";

export const Route = createFileRoute('/_app/challenges/$challengeId/leaderboard')({
    component: RouteComponent,
})

function RouteComponent() {

    const {challengeId} = Route.useParams();

    return (

        <div className="tabbed-page-body">
            <div className="subheader">
                <h2>Leaderboard</h2>
            </div>
            <div className="content">
                <PaginatedWrapperWithSorting
                    fetchPage={(page, pageSize, sortKey, sortOrder, signal) => getSubmissionsByChallenge(challengeId, page, pageSize, sortKey, sortOrder, signal)}
                    renderComponent={({items, sortKey, sortOrder, setSortKey, setSortOrder}) =>
                        <Leaderboard
                            submissions={items}
                            sortKey={sortKey}
                            sortOrder={sortOrder}
                            setSortKey={setSortKey}
                            setSortOrder={setSortOrder}
                        />}
                />
            </div>
        </div>

    );
}

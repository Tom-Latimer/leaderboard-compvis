import {createFileRoute} from '@tanstack/react-router'
import Leaderboard from "./-components/Leaderboard/Leaderboard.tsx";
import {PaginatedWrapper} from "../../../../components/pagination/PaginatedWrapper.tsx";
import {getSubmissionsByChallenge} from "../../../../api/challenges/submissionApi.ts";

export const Route = createFileRoute('/_app/challenges/$challengeId/leaderboard')({
    component: RouteComponent,
})

function RouteComponent() {

    const {challengeId} = Route.useParams();

    return (
        // <Leaderboard submissions={Route.useLoaderData()}/>
        <PaginatedWrapper
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
    );
}

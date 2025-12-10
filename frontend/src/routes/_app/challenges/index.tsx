import {createFileRoute} from '@tanstack/react-router'
import ChallengeList from "../-components/ChallengeList/ChallengeList.tsx";
import {getChallenges} from "../../../api/challenges/challengeApi.ts";
import "./challenges.css";
import {PaginatedWrapper} from "../../../components/pagination/PaginatedWrapper.tsx";

export const Route = createFileRoute('/_app/challenges/')({
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <div className="constrainer">
            <div className="page-header">
                <h1>All Challenges</h1>
            </div>
            <PaginatedWrapper
                fetchPage={(page, pageSize, signal) => getChallenges(page, pageSize, signal)}
                renderComponent={({items}) =>
                    <ChallengeList items={items}/>
                }
            />
        </div>

    );
}

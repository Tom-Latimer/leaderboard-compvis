import {createFileRoute, Outlet} from '@tanstack/react-router'
import {PaginatedWrapperWithSorting} from "../../components/pagination/PaginatedWrapperWithSorting.tsx";
import SubmissionList from "./-components/SubmissionList/SubmissionList.tsx";
import {getSubmissionListPaged} from "../../api/adminSubmissionApi.ts";
import type {SubmissionFilter} from "../../types/submissions/submissionFilter.ts";

export const Route = createFileRoute('/admin/submissions')({
    component: RouteComponent,
})

function RouteComponent() {

    const filter: SubmissionFilter = {
        status: "PENDING"
    };

    return (
        <>
            <h1>Submissions</h1>
            <PaginatedWrapperWithSorting
                fetchPage={(page, pageSize, sortKey, sortOrder, signal) => getSubmissionListPaged(filter, page, pageSize, sortKey, sortOrder, signal)}
                renderComponent={({items, sortKey, sortOrder, setSortKey, setSortOrder}) =>
                    <SubmissionList
                        items={items}
                        sortKey={sortKey}
                        sortOrder={sortOrder}
                        setSortKey={setSortKey}
                        setSortOrder={setSortOrder}
                    />}
            />
            <Outlet/>
        </>
    );
}

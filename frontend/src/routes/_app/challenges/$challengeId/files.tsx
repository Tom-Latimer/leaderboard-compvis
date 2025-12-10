import {createFileRoute} from '@tanstack/react-router'
import {getDatasets} from "../../../../api/challenges/challengeApi.ts";
import DatasetList from "./-components/DatasetList/DatasetList.tsx";
import {PaginatedWrapper} from "../../../../components/pagination/PaginatedWrapper.tsx";

export const Route = createFileRoute('/_app/challenges/$challengeId/files')({
    component: RouteComponent,
})

function RouteComponent() {

    const {challengeId} = Route.useParams();

    return (
        <div className="tabbed-page-body">
            <div className="subheader">
                <h2>Datasets</h2>
            </div>
            <div className="content">

                <PaginatedWrapper
                    fetchPage={(page, pageSize, signal) => getDatasets(challengeId, page, pageSize, signal)}
                    renderComponent={({items}) =>
                        <DatasetList items={items}/>
                    }
                />
            </div>
        </div>
    );
}

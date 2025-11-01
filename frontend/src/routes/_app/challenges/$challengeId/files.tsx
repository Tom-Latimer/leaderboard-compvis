import {createFileRoute} from '@tanstack/react-router'
import {getDatasets} from "../../../../api/challenges/challengeApi.ts";
import DatasetList from "../../../../components/DatasetList/DatasetList.tsx";

export const Route = createFileRoute('/_app/challenges/$challengeId/files')({
    component: RouteComponent,
    loader: async ({params}) => {
        return await getDatasets(params.challengeId, 0, 10);
    }
})

function RouteComponent() {
    return (
        <DatasetList datasets={Route.useLoaderData()}></DatasetList>
    );
}

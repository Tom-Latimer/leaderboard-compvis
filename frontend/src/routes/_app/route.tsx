import {createFileRoute, Outlet} from '@tanstack/react-router'
import Navbar from "./-components/navbar/Navbar.tsx";

export const Route = createFileRoute('/_app')({
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <>
            <Navbar/>
            <div className="page-content">
                <Outlet/>
            </div>
        </>
    );
}
import {createFileRoute, Outlet} from '@tanstack/react-router'
import AdminSidebar from "./-components/AdminSidebar/AdminSidebar.tsx";
import "./admin.css";

export const Route = createFileRoute('/admin')({
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <div className="page-container">
            <AdminSidebar/>
            <main className="main-content">
                <Outlet/>
            </main>
        </div>
    );
}

import {createFileRoute, Outlet, redirect} from '@tanstack/react-router'
import AdminSidebar from "./-components/AdminSidebar/AdminSidebar.tsx";
import "./admin.css";

export const Route = createFileRoute('/admin')({
    beforeLoad: ({context, location}) => {
        if (!context.auth.isAuthenticated) {
            throw redirect({
                to: "/login",
                search: {
                    redirect: location.href,
                }
            })
        }
    },
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

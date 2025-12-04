import {createFileRoute, redirect} from '@tanstack/react-router'
import LoginForm from "./-components/LoginForm.tsx";
import {z} from "zod";

const fallbackRoute = "/admin";

export const Route = createFileRoute('/_auth/login')({
    validateSearch: z.object({
        redirect: z.string().optional().catch(''),
    }),
    beforeLoad: ({context, search}) => {
        if (context.auth.isAuthenticated) {
            throw redirect({to: search.redirect || fallbackRoute})
        }
    },
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <LoginForm/>
    );
}

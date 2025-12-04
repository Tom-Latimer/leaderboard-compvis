import {RouterProvider} from "@tanstack/react-router";
import router from "./router.tsx";
import AuthProvider, {useAuth} from "./components/auth/AuthProvider.tsx";

function InnerApp() {
    const auth = useAuth();
    return <RouterProvider router={router} context={{auth}}/>;
}

function App() {

    return (
        <>
            <AuthProvider>
                <InnerApp/>
            </AuthProvider>
        </>
    );
}

export default App;

import './App.css'
import {RouterProvider} from "@tanstack/react-router";
import router from "./router.tsx";
import AuthProvider from "./components/auth/AuthProvider.tsx";

function App() {

    return (
        <>
            <AuthProvider>
                 <RouterProvider router={router}/>
            </AuthProvider>
        </>
    );
}

export default App

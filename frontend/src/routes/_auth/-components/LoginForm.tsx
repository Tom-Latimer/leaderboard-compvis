import './login-form.css';
import {useNavigate, useRouter} from "@tanstack/react-router";
import {useAuth} from "../../../components/auth/AuthProvider.tsx";
import {type SubmitHandler, useForm} from "react-hook-form";
import {z} from "zod";
import {zodResolver} from "@hookform/resolvers/zod";
import axios from "axios";
import type {ApiError} from "../../../types/errors/ApiError.ts";

const schema = z.object({
    email: z.email()
        .min(1, "Please enter your username")
        .max(50, "Username cannot exceed max 50"),
    password: z.string()
        .min(12, "Please enter valid password")
        .max(20, "Please enter valid password"),
});

type FormInputs = z.infer<typeof schema>;

const LoginForm = () => {

    const {login} = useAuth();

    const router = useRouter();
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        formState: {errors},
        setError
    } = useForm<FormInputs>({
        resolver: zodResolver(schema)
    });

    const onSubmit: SubmitHandler<FormInputs> = async (data) => {

        try {

            if (!data.email || !data.password) {
                return;
            }

            await login(data.email, data.password);

            await router.invalidate();

            await navigate({to: '/admin'});
        } catch (error: unknown) {
            if (axios.isAxiosError(error) && error.response?.data) {
                //convert the error to the standard sent by the backend
                const apiError = error.response.data as ApiError;

                //display errors returned from the backend
                switch (apiError.errorCode) {
                    case "ERR_CRED_INVALID":
                        setError("root.apiError", {
                            message: "Invalid username or password.",
                        });
                        return;
                }
            }

            //generic error
            setError("root.apiError", {message: "An error has occurred while logging in."});
        }
    }

    return (
        <div className="centered-container">
            <div className="login-form-container">
                <div className="login-form-header">
                    <h2>Login</h2>
                    <p>Please enter your credentials to continue</p>
                </div>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="login-form-control">
                        <label htmlFor="email">Email</label>
                        <input type="email" {...register("email")} placeholder="jane.doe@example.com"/>
                        {errors.email && (
                            <div className="error-message">{errors.email.message}</div>)}
                    </div>
                    <div className="login-form-control">
                        <label htmlFor="password">Password</label>
                        <input type="password" {...register("password")}/>
                        {errors.password && (
                            <div className="error-message">{errors.password.message}</div>)}
                    </div>
                    {errors.root?.apiError && (
                        <div className="error-message">
                            {errors.root.apiError.message}
                        </div>
                    )}
                    <div>
                        <input type="submit" value="LOGIN" className="login-form-button"/>
                    </div>
                </form>
                <div className="login-form-footer">
                </div>
            </div>
        </div>
    );
}

export default LoginForm;
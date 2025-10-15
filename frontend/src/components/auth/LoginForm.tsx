import './login.css';
import '../../styles/utility.css';
import {Link, useNavigate} from "@tanstack/react-router";
import React, {useState} from "react";
import {useAuth} from "./AuthProvider.tsx";

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const {login} = useAuth();

    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        await login(email, password);

        await navigate({to: '/'});
    }

    return (
        <div className="centered-container">
            <div className="login-form-container">
                <div className="login-form-header">
                    <h2>Welcome Back</h2>
                    <p>Enter your email and password to access your account.</p>
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="login-form-control">
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" name="email" placeholder="Please enter your email address"
                               value={email} onChange={(e) => {
                            setEmail(e.target.value)
                        }}/>
                    </div>
                    <div className="login-form-control">
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" value={password} onChange={(e) => {
                            setPassword(e.target.value)
                        }}/>
                    </div>
                    <div>
                        <input type="submit" value="Login" className="login-form-button"/>
                    </div>
                </form>
                <div className="login-form-footer">
                    <p>Don't have an account? <span><Link to="/signup">Register Now</Link></span></p>
                </div>
            </div>
        </div>
    );
}

export default LoginForm;
import {Link} from "@tanstack/react-router";
import './navbar.css';

const Navbar = () => {

    return (
        <div className="nav-container">
            <nav className="navbar">
                <Link to="/challenges" className="nav-link" activeProps={{className: "nav-link-active"}}>All
                    Challenges</Link>
            </nav>
        </div>
    );
}

export default Navbar;
import './challenge-list.css';
import type {Challenge} from "../../types/challenges/challenge.tsx";
import {Link} from "@tanstack/react-router";

const ChallengeCard = ({challenge}: { challenge: Challenge }) => {
    return (
        <Link to='/challenges/$challengeId/overview' params={{challengeId: challenge.id}}
              className="challenge-card">
            <div className="challenge-card-body">
                <h3>{challenge.title}</h3>
                <p>{challenge.description}</p>
            </div>
        </Link>
    );
}

const ChallengeList = ({challenges}: { challenges: Challenge[] }) => {

    if (challenges.length === 0) {
        return (
            <p>No challenges found.</p>
        );
    }

    return (
        <div className="challenge-list">
            {
                challenges.map(challenge => (
                    <ChallengeCard key={challenge.id} challenge={challenge}/>
                ))
            }
        </div>
    );
}

export default ChallengeList;
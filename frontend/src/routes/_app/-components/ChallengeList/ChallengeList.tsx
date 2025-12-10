import type {Challenge} from "../../../../types/challenges/challenge.ts";
import {Link} from "@tanstack/react-router";
import "./challenge-list.css";
import PlaceholderIcon from "../../../../assets/placeholder-image.svg?react";

const ChallengeCard = ({challenge}: { challenge: Challenge }) => {
    return (
        <Link to='/challenges/$challengeId/overview' params={{challengeId: challenge.id}}
              className="challenge-card">
            <div className="challenge-card-image">
                <PlaceholderIcon/>
            </div>
            <div className="challenge-card-body">
                <h3>{challenge.title}</h3>
                <p>{challenge.description}</p>
            </div>
        </Link>
    );
}

const ChallengeList = ({items}: { items: Challenge[] }) => {

    if (items.length === 0) {
        return (
            <p>No challenges found.</p>
        );
    }

    return (
        <div className="challenge-list">
            {
                items.map(challenge => (
                    <ChallengeCard key={challenge.id} challenge={challenge}/>
                ))
            }
        </div>
    );
}

export default ChallengeList;
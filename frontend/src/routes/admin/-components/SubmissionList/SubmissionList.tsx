import type {SubmissionListItem} from "../../../../types/submissions/submissionListItem.ts";
import {useEffect} from "react";
import type {PaginatedListProps, SortOrder} from "../../../../types/pagination/paginatedListProps.ts";
import UpArrowIcon from "../../assets/up-arrow.svg?react";
import DownArrowIcon from "../../assets/down-arrow.svg?react";

type SortKey = "dateSubmitted";

const SubmissionListRow = ({submission}: { submission: SubmissionListItem }) => {

    return (
        <tr>
            <td>{submission.submissionId}</td>
            <td>{submission.challengeName}</td>
            <td>{submission.status}</td>
            <td>{submission.dateSubmitted}</td>
            <td></td>
        </tr>
    );
}

const SubmissionListHeader = ({displayValue, value, sortOrder, sortKey, className, onClick}:
                              {
                                  displayValue: string,
                                  value: SortKey,
                                  sortOrder: SortOrder | null,
                                  sortKey: string | null,
                                  className: string,
                                  onClick: (key: SortKey) => void,
                              }) => {

    return (
        <th className={className} onClick={() => onClick(value)}>
            {displayValue}
            <span>
                {sortKey && sortKey === value ? (sortOrder === "asc" ? <UpArrowIcon className="active-icon"/> :
                        <DownArrowIcon className="active-icon"/>) :
                    <DownArrowIcon/>}
            </span>
        </th>
    );
}

const SubmissionList = ({
                            items,
                            sortKey,
                            sortOrder,
                            setSortKey,
                            setSortOrder
                        }: PaginatedListProps<SubmissionListItem>) => {
    //set initial sort
    useEffect(() => {
        setSortKey("maxPrecision");
    }, []);

    const handleSort = (key: SortKey) => {
        if (sortKey === key) {
            setSortOrder(sortOrder === "asc" ? "desc" : "asc");
        } else {
            setSortKey(key as string);
            setSortOrder("desc");
        }
    }

    return (
        <div className="leaderboard-container">
            <div className="leaderboard-wrapper">
                <table className="leaderboard">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Challenge</th>
                        <th>Status</th>
                        <SubmissionListHeader
                            displayValue="Date Submitted"
                            value="dateSubmitted"
                            sortOrder={sortOrder}
                            sortKey={sortKey}
                            className="sortable-header"
                            onClick={handleSort}/>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {items.length !== 0 ? (

                        items.map((sub) => (
                            <SubmissionListRow
                                key={sub.submissionId}
                                submission={sub}
                            />
                        ))) : (
                        <tr>
                            <td colSpan={4}></td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default SubmissionList;
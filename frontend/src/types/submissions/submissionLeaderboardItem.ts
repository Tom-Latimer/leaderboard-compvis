import type {OrderedItem} from "../pagination/orderedItem.ts";

export class SubmissionLeaderboardItem implements OrderedItem {
    submissionId: string;
    submitterFirstName: string;
    submitterLastName: string;
    submitterEmail: string;
    maxPrecision: number;
    maxRecall: number;
    split: number;
    rank: number;

    constructor(submissionId: string, submitterFirstName: string, submitterLastName: string, submitterEmail: string,
                maxPrecision: number, maxRecall: number, split: number, rank: number) {
        this.submissionId = submissionId;
        this.submitterFirstName = submitterFirstName;
        this.submitterLastName = submitterLastName;
        this.submitterEmail = submitterEmail;
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
        this.rank = rank;
    }

    getName(): string {
        return this.submitterFirstName + " " + this.submitterLastName;
    }
}
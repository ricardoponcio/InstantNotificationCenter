import { Group, JobUpdate } from "./";

export class Job {

    id !: number;
    name !: string;
    description !: string;
    
    startDate !: Date;
    endDate !: Date;
    lastUpdate !: Date;

    finished !: boolean;
    status !: string;
    resultMessage !: string;

    group !: Group;
    updates !: JobUpdate[];

    collapseDetail: boolean = true;

}
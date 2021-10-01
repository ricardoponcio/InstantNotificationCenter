import { IconName, IconNamesEnum } from "ngx-bootstrap-icons";

export class Job {

    name !: string;
    description !: string;
    group !: string;
    percent !: number;
    logs !: string[];
    
    startDate !: Date;
    endDate !: Date;
    lastUpdate !: Date;

    finished !: boolean;
    status !: string;
    resultMessage !: string;

    collapseDetail: boolean = true;

    getSuffixState = () => {
        if (!this.finished) return 'secondary'; 
        if (this.status == 'SUCCESS') return 'success';
        if (this.status == 'ERROR') return 'danger';
        if (this.status == 'EXPIRED') return 'warning';
        return '';
    }

    getHex = () : string => {
        if (!this.finished) return 'inherit'; 
        if (this.status == 'SUCCESS') return '#198754';
        if (this.status == 'ERROR') return '#dc3545';
        if (this.status == 'EXPIRED') return '#ffc107';
        return '#6c757d';
    }

    getHexCard = () : string => {
        if (!this.finished) return 'inherit'; 
        if (this.status == 'SUCCESS') return '#d1e7dd';
        if (this.status == 'ERROR') return '#f8d7da';
        if (this.status == 'EXPIRED') return '#fff3cd';
        return '#e2e3e5';
    }

    getHexText = () : string => {
        if (!this.finished) return 'inherit'; 
        if (this.status == 'SUCCESS') return '#0f5132';
        if (this.status == 'ERROR') return '#842029';
        if (this.status == 'EXPIRED') return '#664d03';
        return '#41464b';
    }

    getInfoAlert = () : string => {
        if (!this.finished) return ''; 
        if (this.status == 'SUCCESS') return 'Job finished successfully!';
        if (this.status == 'ERROR') return 'Job finished with an error! ' + (this.resultMessage ? 'Detail: ' + this.resultMessage : '');
        if (this.status == 'EXPIRED') return 'Job has expired!';
        return 'Status not recognized!';
    }

    getIconState = () : IconName => {
        if (!this.finished) return IconNamesEnum.Clock; 
        if (this.status == 'SUCCESS') return IconNamesEnum.CheckCircle;
        if (this.status == 'ERROR') return IconNamesEnum.XCircle;
        if (this.status == 'EXPIRED') return IconNamesEnum.ExclamationCircle;
        return IconNamesEnum.QuestionCircle;
    }

}
import { SecretsUse } from './SecretsUse';

export class Secrets {

    id !: number;
    name !: string;
    desription !: string;
    secret !: string;
    active !: boolean;

    secretsUse !: SecretsUse[];

}

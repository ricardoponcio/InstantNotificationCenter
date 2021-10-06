export class ResponseEntity<T> {

    status !: boolean;
    code !: number;
    message !: string;
    attach !: T;

    constructor() {}

}
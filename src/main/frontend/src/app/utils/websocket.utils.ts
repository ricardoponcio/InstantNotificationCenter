// Declare SockJS and Stomp
declare var SockJS: new (arg0: string) => any;
declare var Stomp: { over: (arg0: any) => any; };

import { environment } from '../../environments/environment';

export class WebSocketUtils {

    private stompClient !: any;

    private tries: Date[] = [];
    private maxTries = 5;
    private maxSecondsWait = 60;

    constructor(
        private webSocketEndPoint: string,
        private topic: string,
        private onMessage: Function,
        private headers?: any,
        private callbackError?: Function) {
        const errorCallback = this.callbackError || this.onError;
        this.connect(errorCallback);
    }

    private connect(errorCallback: Function) {
        // console.log("Starting a WebSocket connection");
        const ws = new SockJS(environment.url + this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.debug = () => {};
        const that = this;
        this.tries.push(new Date());
        this.stompClient.connect(this.headers || {}, (frame: any) => {
            that.stompClient.subscribe(this.topic, (event: any) => {
                that.onMessage(JSON.parse(event.body));
            })
        }, errorCallback.bind(this));
    }

    private onError(error: any) {
        // console.log("Error while connect: " + error);
        const triesUntilNow = this.tries.filter((lastTry: Date) => this.difference(lastTry, new Date()) < this.maxSecondsWait).length;
        if (triesUntilNow < this.maxTries) {
            setTimeout(() => {
                // console.log("Trying to connect again...");
                this.connect(this.onError);
            }, 1000);
        }
    }

    public sendMessage(message: any) {
        this.stompClient.send('/app/' + this.topic, {}, message);
    }

    private difference(a: any, b: any): number {
        return (b - a) / 1000;
    }

}
export interface CreditCard {
    cardNum: string,
    cardExpMth: number,
    cardExpYear: number,
    cardCvc: string
}

export interface Subscription extends CreditCard {
    subName: string,
    subDesc: string,
    unitAmount: number,
    interval: string
}
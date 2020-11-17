/** this is the feed Params class*/
export class FeedParams {
    /**
     * @constructor
     */
    constructor(
    /** startDate of the advanced filter*/
    public startDate:Date,

    /** endDate of the advanced filter*/
    public endDate:Date,

    /** @ignore */
    public page:string,

    /** @ignore */
    public size:string,

    /** This is the userId from Current session */
    public userId?,

    /**This is the teamId of the user */
    public teamId?
    ){}
}

import $ from "jquery";

export class SpringRestOnlyParallelUnlimitedProcessor {

    $processingArea;
    #$squareArea;
    #squares;
    #endpointUrl;

    startDate;
    endDate;
    timeSpent;

    constructor($processingArea, endpointUrl) {
        this.$processingArea = $processingArea;
        this.#$squareArea = this.$processingArea.children('.squares');
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.startDate = new Date();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyParallelUnlimitedProcessor start...');

        let promises = new Array();

        for (let square of this.#squares) {
            promises.push(this.#processSquare($(square)));
        }

        await Promise.all(promises);

        this.endDate = new Date();
        this.timeSpent = (this.endDate - this.startDate) / 1000;
    }

    async #processSquare($square) {
        $square.addClass('processing');

        await new Promise((resolve) => {
            $.post({
                url: this.#endpointUrl,
                data: JSON.stringify({ data: JSON.stringify({ storedNumer: Math.floor(Math.random() * 999999999) }) }),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: () => {
                    $square.removeClass('processing').addClass('success');
                }
            }).fail(() => {
                $square.removeClass('processing').addClass('fail');
            }).always(() => {
                resolve();
            });
        })
    }
}
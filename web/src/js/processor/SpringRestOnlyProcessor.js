import $ from "jquery";

export class SpringRestOnlyProcessor {

    #$squareArea;

    constructor($squareArea) {
        this.#$squareArea = $squareArea;
    }

    process() {
        let $defer = $.Deferred();

        console.debug('SpringRestOnlyProcessor start...');
        this.#processRequest(this.#$squareArea.children('.square').get()).always(() => {
            $defer.resolve();
        });

        return $defer.promise();
    }

    #processRequest(requestSquares) {
        let $defer = $.Deferred();

        let requestSquare = requestSquares.shift();

        if (!requestSquare) {
            return $defer.resolve().promise();
        }

        let $requestSquare = $(requestSquare);

        $requestSquare.addClass('processing');

        $.post('http://localhost:8080/time-registries', () => {
            $requestSquare.removeClass('processing').addClass('success');
        }).fail(() => {
            $requestSquare.removeClass('processing').addClass('fail');
        }).always(() => {
            this.#processRequest(requestSquares).always(() => {
                $defer.resolve();
            });
        });;

        return $defer.promise();
    }
}
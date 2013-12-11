/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

!function ($) {

    /* FACE PART CLASS DEFINITION
     * ========================= */
    var FacePart = function (element) {
        this.$element = $(element);

        this.part = this.$element.data('part');
        this.value = this.$element.data('value');
        this.$picture = this.$element.find('svg > g');

        var selector = this.$element.data('target');
        this.$target = selector && $(selector).find('g[data-part=' + this.part + ']');
        this.$input = selector && $(selector).find('input[data-part=' + this.part + ']');
    };

    FacePart.prototype = {
        constructor: FacePart,

        show: function () {
            // redraw face with new part
            this.$target && this.$target.empty().append(this.$picture.children().clone());
            // save chosen value
            this.$input && this.$input.val(this.value);

            // activate new menu item
            $('.active[data-toggle=face-part][data-part=' + this.part + ']').removeClass('active');
            this.$element.addClass('active');
        }
    };

    /* FACE PART PLUGIN DEFINITION
     * ========================== */
    $.fn.facePart = function (option) {
        return this.each(function () {
            var $this = $(this);
            var data = $this.data('facePart');
            if (!data) $this.data('facePart', (data = new FacePart(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.facePart.Constructor = FacePart;


    /* FACE TITLE CLASS DEFINITION
     * ========================= */
    var FaceTitle = function (element) {
        this.$element = $(element);

        var selector = this.$element.data('target');
        var part = this.$element.data('part');
        this.$target = selector && $(selector).find('g[data-part=' + part + ']');
        this.$input = selector && $(selector).find('input[data-part=' + part + ']');
    };

    FaceTitle.prototype = {
        constructor: FaceTitle,

        refresh: function () {
            var value = this.$element.val();

            // redraw face with new title
            this.$target && this.$target.find('text').text(value);
            // save chosen value
            this.$input && this.$input.val(value);
        }
    };

    /* FACE TITLE PLUGIN DEFINITION
     * ========================== */
    $.fn.faceTitle = function (option) {
        return this.each(function () {
            var $this = $(this);
            var data = $this.data('faceTitle');
            if (!data) $this.data('faceTitle', (data = new FaceTitle(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceTitle.Constructor = FaceTitle;

    /* FACE GENERATOR CLASS DEFINITION
     * ========================= */
    var FaceGenerator = function (element, options) {
        this.$element = $(element);
        this.names = options.names || [];
    };

    FaceGenerator.prototype = {
        constructor: FaceGenerator,

        randomize: function () {
            var self = this;
            var $choices = $('[data-toggle=face-part][data-target=#' + this.$element.attr('id') + ']');
            var parts = [];
            $choices.each(function () {
                var $this = $(this);
                var part = $this.data('part');

                if ($.inArray(part, parts) < 0) {
                    parts.push(part);
                }
            });

            $.each(parts, function () {
                self._randomize($choices, this);
            });

            this._randomizeTitle();
        },

        _randomize: function ($all, part) {
            var $choices = $all.filter('[data-part=' + part + ']');

            var randomChoice = Math.floor(Math.random() * $choices.length);
            $choices.eq(randomChoice)
                .facePart('show');
        },

        _randomizeTitle: function () {
            if (this.names) {
                var randomChoice = Math.floor(Math.random() * this.names.length);
                var name = this.names[randomChoice];
                $('[data-toggle=face-title][data-target=#' + this.$element.attr('id') + ']')
                    .val(name)
                    .faceTitle('refresh');
            }
        }
    };

    /* FACE GENERATOR PLUGIN DEFINITION
     * ========================== */
    $.fn.faceGenerator = function (option) {
        return this.each(function () {
            var $this = $(this);
            var data = $this.data('faceGenerator');
            var options = $.extend({}, $this.data(), typeof option == 'object' && option);

            if (!data) $this.data('faceGenerator', (data = new FaceGenerator(this, options)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceGenerator.Constructor = FaceGenerator;

    /* DATA-API
     * ============== */
    $(function () {
        $(document)
            .on('click.mutabra.data-api', '[data-toggle=face-part]', function () {
                var $this = $(this);
                var $selected = $this.closest('[data-toggle=face-part]') || $this;

                $selected.facePart('show');
            })
            .on('change.mutabra.data-api', '[data-toggle=face-title]', function () {
                $(this).faceTitle('refresh');
            })
            .on('dblclick.mutabra.data-api', '[data-toggle=face-generator]', function () {
                $(this).faceGenerator('randomize');
            })
            .on('click.mutabra.data-api', '[data-toggle=face-part][data-part=all]', function() {
                var $this = $(this);
                var value = $this.data('value');
                var $selected = $('[data-value=' + value +']');

                $selected.siblings('.active').removeClass('active');
                $selected.addClass('active');
            });

        // face parts with title should be as tooltip
        $('.face-part[title]').tooltip();
    });

}(window.jQuery);
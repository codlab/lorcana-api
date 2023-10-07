import * as fs from "fs";

function mapLower(obj: any) {
    return Object.keys(obj).reduce((acc: any, k: string) => {
        acc[k.toLowerCase()] = obj[k];
        return acc;
    }, {});
}

interface Language {
    name: string,
    title: string,
    flavour: string,
    image: string
}

interface Languages {
    [key: string]: Language
}

function filterNonNull(languages: Languages) {
    return Object.keys(languages).reduce((acc: Languages, k: string) => {
        const subObject = languages[k];
        const hasNonNull = !!Object.keys(subObject).find((k: keyof Language) => {
            // unknown, likely name or title
            if (!subObject[k]) return false;

            if (`${subObject[k]}`.toLowerCase() == "unknown") {
                return false;
            }

            // card back likely the image
            if (`${subObject[k]}`.indexOf("cards%2Fback") >= 0) {
                return false;
            }

            return true;
        });

        if (hasNonNull) {
            acc[k.toLowerCase()] = languages[k];
        }

        return acc;
    }, {});
}

function mapDescription(actionDescription: string) {
    const leftHand = /^<i>([^<]*)<\/i>\s?(.*)/; // matches <i>_anything
    const rightHand = /^([^<]*)\s?<i>([^<]*)(<\/i>)?/; // matches anything_<i>

    const isUsingLeft = actionDescription.match(leftHand);
    const isUsingRight = actionDescription.match(rightHand);

    const clean = (str: string) => {
        return str.replace("<br />", " ").trim()
    }

    if (isUsingLeft) {
        return [
            { type: "note", text: clean(isUsingLeft[1]) },
            { type: "text", text: clean(isUsingLeft[2]) },
        ].filter(({text}) => !!text);
    } else if(isUsingRight) {
        return [
            { type: "text", text: clean(isUsingRight[1]) },
            { type: "note", text: clean(isUsingRight[2]) },
        ].filter(({text}) => !!text);
    } else {
        return [
            { type: "text", text: clean(actionDescription) },
        ].filter(({text}) => !!text);
    }
}

function mapAction(action: string) {
    action = action.replace(/\n/g, " ").replace(/<br \/>/g, "<br>");
    if (action.indexOf("PUNY PIRATE") > 0) {
        console.log(action)
    }

    return action.split("<br>").map(action => {
        //const reg = /<[^>]*>([^<]*)<\/[^>]*>\s?(.*)/;
        // this would catch also those starting with i, which will probably need more
        // for now we can just use the mark|b
        const reg = /<(b|mark)>([^<]*)<\/(b|mark)>\s?(.*)/;

        const found = action.match(reg);

        if (!found) {
            return {
                description: mapDescription(action)
            };
        }

        return { // found[0] = original string
            name: found[2].trim(),
            description: mapDescription(found[4])
        };
    });
}

function mapCard(card: any) {
    if (card.action) {
        card.actions = mapAction(card.action);
    }
    delete card.action;

    if (card.card_set_id) {
        card.setCode = (() => {
            switch(card.card_set_id) {
                case 2: return "tfc";
                case 1: return "d23";
                default: return "rotf"
            }
        })();
        delete card.card_set_id;
    }

    if (card.languages) {
        Object.keys(card.languages).forEach(key => {
            const holder = card.languages[key];

            if (holder.card_id) delete holder.card_id;
            if (holder.language) delete holder.language;

            if (holder.action) {
                holder.actions = mapAction(holder.action);
            }
            delete holder.action;
        });
        card.languages = filterNonNull(mapLower(card.languages));
    }

    return card
}

function cleanUp(set: string, content: string) {
    const json = JSON.parse(content);
    const cleaned = Object.keys(json.cards)
        .map(key => mapCard(json.cards[key]));

    // now mapping the old card to the actual schema

    const output = JSON.stringify(cleaned, null, 2);
    fs.writeFileSync(`../../lorcana-shared/src/commonMain/resources/MR/files/${set}.json`, output);
    return cleaned;
}

[ "d23", "tfc", "rotf"].map( key => cleanUp(key, fs.readFileSync(`../sets/${key}.json`, "utf-8")));

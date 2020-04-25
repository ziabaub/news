
export let compareByTitle = (a, b) => {
    const titleA = a.title.replace(/ .*/,'').toUpperCase();
    const titleB = b.title.replace(/ .*/,'').toUpperCase();
    let comparison = 0;
    if (titleA > titleB) {
        comparison = 1;
    } else if (titleA < titleB){
        comparison = -1;
    }
    return comparison;
};


export const compareByAuthor = (a, b) => {
    const authorA = a.author.name.toUpperCase();
    const authorB = b.author.name.toUpperCase();

    let comparison = 0;
    if (authorA > authorB) {
        comparison = 1;
    } else if (authorA < authorB){
        comparison = -1;
    }
    return comparison;
};
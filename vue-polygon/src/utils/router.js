import Layout from '@/layout'

export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            meta,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        var temp
        if (component.startsWith("layout")) {
            temp = Layout
        } else{
          temp = (resolve) => require([`@/views/${component}`], resolve)
        }
        let fmRouter = {
            path: path,
            name: name,
            meta: meta,
            children: children,
            component: temp
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}
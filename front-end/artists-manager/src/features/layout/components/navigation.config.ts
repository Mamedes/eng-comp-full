import { Activity, Disc, LayoutDashboard } from "lucide-react";

export const NAV_ITEMS = [
  {
    label: "Dashboard",
    href: "/",
    icon: LayoutDashboard,
  },
  {
    label: "Álbuns",
    href: "/albums",
    icon: Disc,
  },
  {
    label: "Status",
    href: "/status",
    icon: Activity,
  },
];
